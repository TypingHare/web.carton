package burrow.carton.web.command

import burrow.carton.core.Core
import burrow.carton.hoard.RecordNotFoundException
import burrow.carton.web.Web
import picocli.CommandLine
import burrow.carton.core.command.NotFoundCommand as OriginalNotFoundCommand

@CommandLine.Command(name = Core.NOT_FOUND_COMMAND_NAME)
class NotFoundCommand : OriginalNotFoundCommand() {
    override fun call(): Int {
        val web = use(Web::class)
        val first = commandList.first()
        val silentlyDispatch = web.spec.silentlyDispatch

        try {
            val webRecord = web.getWebRecordByName(first)

            if (!silentlyDispatch) {
                stdout.println("Dispatching '$first' to the open command")
            }

            dispatch(OpenCommand::class, webRecord.name)
        } catch (_: RecordNotFoundException) {
            stderr.println("Failed to dispatch $first because no web records are found.")

            super.call()
        }

        return CommandLine.ExitCode.OK
    }
}