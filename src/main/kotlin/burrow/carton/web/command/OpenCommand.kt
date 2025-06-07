package burrow.carton.web.command

import burrow.carton.core.command.CoreCommand
import burrow.carton.cradle.Cradle
import burrow.carton.hoard.HoardPair
import burrow.carton.web.WebRecord
import picocli.CommandLine

@CommandLine.Command(
    name = "open",
    header = ["Open a webpage associated with a specified name."]
)
class OpenCommand : CoreCommand() {
    @CommandLine.Parameters(
        index = "0",
        description = ["The name identifier of the webpage to open."]
    )
    private var name = ""

    override fun call(): Int {
        super.call()

        @Suppress("USELESS_CAST")
        val record = use(HoardPair::class).idSetMap
            .getOrDefault(name, emptySet())
            .firstOrNull() as WebRecord?
        if (record == null) {
            stderr.println("No webpage found with the name: $name")
            return CommandLine.ExitCode.USAGE
        }

        val url = record.url
        stdout.println("Opening webpage at: $url")
        use(Cradle::class).executeCommand(listOf("open", url), this)

        return CommandLine.ExitCode.OK
    }
}