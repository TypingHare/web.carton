package burrow.carton.web.command

import burrow.carton.hoard.Hoard
import burrow.carton.hoard.HoardPair
import burrow.carton.hoard.HoardTag
import burrow.common.palette.Highlight
import burrow.common.palette.PicocliPalette
import burrow.common.palette.Style
import burrow.kernel.terminal.BurrowCommand
import burrow.kernel.terminal.Command
import burrow.kernel.terminal.CommandData
import burrow.kernel.terminal.ExitCode

@BurrowCommand(
    name = "list",
    header = ["Displays all web names and associated URLs."]
)
class ListCommand(data: CommandData) : Command(data) {
    override fun call(): Int {
        val palette = PicocliPalette()
        val table = mutableListOf<List<String>>(
            listOf(
                palette.color("ID", Highlight(style = Style.BOLD)),
                palette.color("Name", Highlight(style = Style.BOLD)),
                palette.color("URL", Highlight(style = Style.BOLD)),
                palette.color("Tags", Highlight(style = Style.BOLD)),
            )
        )
        val hoardPair = use(HoardPair::class)
        val hoardTags = use(HoardTag::class)
        use(Hoard::class).storage.getAllEntries().forEach { entry ->
            val name = hoardPair.getKey<String>(entry)
            val url = hoardPair.getValue<String>(entry)
            val tags = hoardTags.getTags(entry)
            table.add(
                listOf(
                    entry.id.toString(),
                    name,
                    url,
                    tags.joinToString("; ")
                )
            )
        }

        printTable(table)

        return ExitCode.OK
    }
}