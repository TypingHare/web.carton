package burrow.carton.web.command

import burrow.carton.core.command.CoreCommand
import burrow.carton.cradle.Cradle
import burrow.carton.hoard.Hoard
import burrow.carton.hoard.HoardPair
import burrow.carton.web.record.WebRecord
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

    @Suppress("HttpUrlsUsage")
    override fun call(): Int {
        super.call()

        val recordId = use(HoardPair::class).getIdSetByKey(name).firstOrNull()
        if (recordId == null) {
            stderr.println("No webpage found with the name: $name")
            return CommandLine.ExitCode.USAGE
        }

        val record = use(Hoard::class).getStorage<WebRecord>().get(recordId)
        val protocolPrefixList = listOf("http://", "https://")
        val url = record.url.let {
            when (protocolPrefixList.none { url -> url.startsWith(it) }) {
                true -> "https://$it"
                false -> it
            }
        }

        stdout.println("Opening webpage at: $url")
        use(Cradle::class).executeCommand(listOf("open", url), this)

        return CommandLine.ExitCode.OK
    }
}