package burrow.carton.web

import burrow.carton.core.Core
import burrow.carton.hoard.Hoard
import burrow.carton.web.command.ListCommand
import burrow.carton.web.command.OpenCommand
import burrow.kernel.Blueprint
import burrow.kernel.chamber.Furnishing
import burrow.kernel.chamber.Furniture
import burrow.kernel.chamber.Renovator
import burrow.kernel.chamber.Spec
import burrow.kernel.chamber.getId

const val VERSION = "0.0.0"
const val REQUIRED_BURROW_VERSION = "0.0.0"

@Suppress("unused")
@Furniture(
    version = VERSION,
    description = "Manages a collection of web bookmarks.",
    type = Furniture.Type.MAIN
)
class Web(
    renovator: Renovator,
    blueprint: Blueprint,
) : Furnishing<WebSpec>(renovator, blueprint) {
    override fun initSpec(): WebSpec =
        WebSpec(
            requires = listOf(getId(Hoard::class))
        )

    override fun assemble() {
        use(Core::class).registerSubCommands(
            ListCommand::class,
            OpenCommand::class
        )
    }
}

data class WebSpec(override val requires: List<String>): Spec(requires)