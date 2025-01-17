package burrow.carton.web.command

import burrow.carton.core.Core
import burrow.carton.web.Web
import burrow.kernel.terminal.BurrowCommand
import burrow.kernel.terminal.Command
import burrow.kernel.terminal.CommandData
import burrow.kernel.terminal.Parameters

@BurrowCommand(
    name = Core.NOT_FOUND_COMMAND_NAME,
    header = ["Dispatches the command name to the 'open' command if it does not exist."]
)
class NotFoundCommand(data: CommandData) : Command(data) {
    @Parameters(
        index = "0",
        description = ["The command name."]
    )
    private var commandName = ""

    override fun call(): Int {
        if (!config.getNotNull<Boolean>(Web.ConfigKey.SILENTLY_DISPATCH)) {
            stdout.println("Command not found: $commandName; dispatching the command name as a website name to the 'open' command.")
        }
        return dispatch(OpenCommand::class, listOf(commandName))
    }
}