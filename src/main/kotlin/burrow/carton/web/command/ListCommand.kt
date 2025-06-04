package burrow.carton.web.command

import burrow.carton.core.command.CoreCommand
import picocli.CommandLine

@CommandLine.Command(
    name = "list",
    header = [""]
)
class ListCommand : CoreCommand() {
    override fun call(): Int {
        super.call()

        stdout.println("This command should display a list of webs.")

        return CommandLine.ExitCode.OK
    }
}