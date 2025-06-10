package burrow.carton.web.command

import burrow.carton.hoard.RecordNotFoundException
import burrow.carton.web.Web
import picocli.CommandLine
import burrow.carton.core.command.NotFoundCommand as OriginalNotFoundCommand

@CommandLine.Command(name = "<not-found>")
class NotFoundCommand : OriginalNotFoundCommand() {
    override fun call(): Int {
        val first = commandList.first()

        try {
            val webRecord = use(Web::class).getWebRecordByName(first)
            dispatch(OpenCommand::class, webRecord.name)
        } catch (_: RecordNotFoundException) {
            super.call()
        }

        return CommandLine.ExitCode.OK
    }
}