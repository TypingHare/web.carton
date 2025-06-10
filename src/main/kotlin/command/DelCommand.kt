package burrow.carton.web.command

import burrow.carton.core.command.CoreCommand
import burrow.carton.hoard.Hoard
import burrow.carton.hoard.HoardPair
import burrow.carton.web.record.WebRecord
import picocli.CommandLine

@CommandLine.Command(
    name = "del",
    header = ["Delete a webpage entry."],
    description = [""]
)
class DelCommand : CoreCommand() {
    @CommandLine.Parameters(
        index = "0",
        description = ["The name or ID of the webpage entry to delete."]
    )
    private lateinit var nameOrId: String

    override fun call(): Int {
        super.call()

        if (nameOrId.toIntOrNull() != null) {
            use(Hoard::class).deleteRecord<WebRecord>(nameOrId.toInt())
            stdout.println("Deleted webpage entry with ID: $nameOrId")
        } else {
            use(HoardPair::class).deleteRecords<WebRecord>(nameOrId)
            stdout.println("Deleted webpage entry with name: $nameOrId")
        }

        return CommandLine.ExitCode.OK
    }
}