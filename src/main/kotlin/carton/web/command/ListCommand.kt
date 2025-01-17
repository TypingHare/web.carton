package burrow.carton.web.command

import burrow.kernel.terminal.BurrowCommand
import burrow.kernel.terminal.Command
import burrow.kernel.terminal.CommandData
import burrow.kernel.terminal.ExitCode

@BurrowCommand(
    name = "list",
    header = ["List all URLs."]
)
class ListCommand(data: CommandData) : Command(data) {
    override fun call(): Int {
        return ExitCode.OK
    }
}