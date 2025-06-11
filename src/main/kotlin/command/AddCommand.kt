package burrow.carton.web.command

import burrow.carton.core.command.CoreCommand
import burrow.carton.hoard.command.pair.AddCommand
import picocli.CommandLine

@CommandLine.Command(
    name = "add",
    header = ["Create a webpage entry."],
    description = [""]
)
class AddCommand : CoreCommand() {
    @CommandLine.Parameters(
        index = "0",
        description = ["The name of the webpage."]
    )
    private lateinit var name: String

    @CommandLine.Parameters(
        index = "1",
        description = ["The URL associated with the webpage name."]
    )
    private lateinit var url: String

    override fun call(): Int {
        super.call()

        dispatch(AddCommand::class, name, url)
        stdout.println("Added webpage entry: $name -> $url")

        return CommandLine.ExitCode.OK
    }
}