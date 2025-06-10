package burrow.carton.web.command

import burrow.carton.core.command.NotFoundCommand as OriginalNotFoundCommand
import burrow.carton.web.Web
import picocli.CommandLine

class NotFoundCommand : OriginalNotFoundCommand() {
    override fun call(): Int {
        use(Web::class).getAllWebRecords()

        return CommandLine.ExitCode.OK
    }
}