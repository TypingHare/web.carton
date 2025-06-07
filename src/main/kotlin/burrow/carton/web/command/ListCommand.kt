package burrow.carton.web.command

import burrow.carton.core.command.CoreCommand
import burrow.carton.hoard.Hoard
import burrow.carton.web.WebRecord
import picocli.CommandLine

@CommandLine.Command(
    name = "list",
    header = ["Display all web entries."]
)
class ListCommand : CoreCommand() {
    override fun call(): Int {
        super.call()

        val records = use(Hoard::class).getAllRecords<WebRecord>()
        records.forEach { record ->
            stdout.println("${record.name} - ${record.url}")
        }

        return CommandLine.ExitCode.OK
    }
}