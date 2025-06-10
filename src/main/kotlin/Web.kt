package burrow.carton.web

import burrow.carton.core.Core
import burrow.carton.cradle.Cradle
import burrow.carton.hoard.DuplicateIdException
import burrow.carton.hoard.Hoard
import burrow.carton.hoard.HoardPair
import burrow.carton.hoard.HoardTime
import burrow.carton.hoard.RecordNotFoundException
import burrow.carton.shell.Shell
import burrow.carton.web.command.*
import burrow.kernel.Blueprint
import burrow.kernel.chamber.*
import burrow.carton.web.record.WebRecord

const val VERSION = "0.0.0"
const val REQUIRED_HOARD_VERSION = "0.0.0"

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
    override fun initSpec(rawSpec: RawSpec) = WebSpec().apply {
        setSpec<Boolean>("silentlyDispatch") { silentlyDispatch = it }
    }

    override fun assemble() {
        use(Core::class).extendSubcommands(
            ListCommand::class,
            OpenCommand::class,
            AddCommand::class,
            DelCommand::class
        )

        use(Core::class).extendSubcommands(NotFoundCommand::class)

        useSpec(Hoard::class).recordClassName = WebRecord::class.java.name

        useSpec(HoardPair::class).let { hoardPairSpec ->
            hoardPairSpec.keyName = "name"
            hoardPairSpec.valueName = "url"
            hoardPairSpec.allowDuplicateKeys = false
        }

        useSpec(Shell::class).shellFileName = "web"
    }

    override fun launch() {
        val shell = use(Shell::class)
        shell.createShellFileIfNotExist(shell.getDefaultShellContent())
    }

    fun getAllWebRecords(): List<WebRecord> =
        use(Hoard::class).getAllRecords()

    @Throws(DuplicateIdException::class, RecordNotFoundException::class)
    fun getWebRecordByName(name: String): WebRecord =
        use(HoardPair::class).getRecordByKey(name)
}

data class WebSpec(
    override var requires: Collection<Dependency> = listOf(
        Dependency(HoardPair::class, REQUIRED_HOARD_VERSION),
        Dependency(HoardTime::class, REQUIRED_HOARD_VERSION),
        Dependency(Cradle::class, REQUIRED_HOARD_VERSION),
        Dependency(Shell::class, REQUIRED_HOARD_VERSION),
    ),
    var silentlyDispatch: Boolean = true
) : Spec(requires)