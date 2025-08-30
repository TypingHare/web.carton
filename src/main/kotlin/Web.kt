package burrow.carton.web

import burrow.carton.core.Core
import burrow.carton.hoard.DuplicateIdException
import burrow.carton.hoard.Hoard
import burrow.carton.hoard.HoardPair
import burrow.carton.hoard.RecordNotFoundException
import burrow.carton.shell.Shell
import burrow.carton.web.command.*
import burrow.carton.web.record.WebRecord
import burrow.kernel.Blueprint
import burrow.kernel.chamber.Furnishing
import burrow.kernel.chamber.Furniture
import burrow.kernel.chamber.RawSpec
import burrow.kernel.chamber.Renovator

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

        if (spec.autoDispatch) {
            use(Core::class).extendSubcommands(NotFoundCommand::class)
        }

        useSpec(Hoard::class).recordClassName = WebRecord::class.java.name

        useSpec(HoardPair::class).let { hoardPairSpec ->
            hoardPairSpec.keyName = "name"
            hoardPairSpec.valueName = "url"
            hoardPairSpec.allowDuplicateKeys = false
        }
    }

    override fun launch() {
        use(Shell::class).createShellFileIfNotExist()
    }

    fun getAllWebRecords(): List<WebRecord> =
        use(Hoard::class).getAllRecords()

    @Throws(DuplicateIdException::class, RecordNotFoundException::class)
    fun getWebRecordByName(name: String): WebRecord =
        use(HoardPair::class).getRecordByKey(name)
}