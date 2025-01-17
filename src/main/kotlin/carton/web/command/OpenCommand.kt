package burrow.carton.web.command

import burrow.carton.cradle.Cradle
import burrow.carton.hoard.HoardPair
import burrow.kernel.terminal.Command
import burrow.kernel.terminal.CommandData
import burrow.kernel.terminal.ExitCode
import burrow.kernel.terminal.Parameters

class OpenCommand(data: CommandData) : Command(data) {
    @Parameters(
        index = "0",
        description = ["The name of the website to open."]
    )
    private var name = ""

    override fun call(): Int {
        val hoardPair = use(HoardPair::class)
        val entries = hoardPair.getEntries(name)
        if (entries.isEmpty()) {
            stdout.println("Website not found: $name")
            return ExitCode.USAGE
        }

        val url = hoardPair.getValue<String>(entries.first())
        use(Cradle::class).executeCommand(
            "open $url", data.environment, stdout, stderr
        )

        return ExitCode.OK
    }
}