package burrow.carton.web.command

import burrow.carton.core.command.CoreCommand
import picocli.CommandLine

@CommandLine.Command(
    name = "open",
    header = ["Opens a website associated with a given name."]
)
class OpenCommand : CoreCommand() {
    @CommandLine.Parameters(
        index = "0",
        description = ["The name of the website to open."]
    )
    private var name = ""

    override fun call(): Int {
        super.call()

        stdout.println(name)

        return CommandLine.ExitCode.OK
    }
}