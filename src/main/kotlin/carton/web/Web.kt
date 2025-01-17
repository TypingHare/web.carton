package burrow.carton.web

import burrow.carton.cradle.Cradle
import burrow.carton.hoard.HoardPair
import burrow.carton.hoard.HoardTag
import burrow.carton.hoard.HoardTime
import burrow.carton.inverse.Inverse
import burrow.carton.inverse.annotation.ConfigItem
import burrow.carton.inverse.annotation.ConverterPairType
import burrow.carton.inverse.annotation.InverseRegisterCommands
import burrow.carton.inverse.annotation.InverseSetConfig
import burrow.carton.shell.Shell
import burrow.kernel.furniture.Furnishing
import burrow.kernel.furniture.Renovator
import burrow.kernel.furniture.annotation.Dependency
import burrow.kernel.furniture.annotation.Furniture
import burrow.kernel.furniture.annotation.RequiredDependencies

const val VERSION = "0.0.0"
const val BURROW_VERSION = "0.0.0"

@Furniture(
    version = VERSION,
    description = "Manages a collection of web bookmarks.",
    type = Furniture.Type.MAIN
)
@RequiredDependencies(
    Dependency(Inverse::class, BURROW_VERSION),
    Dependency(Cradle::class, BURROW_VERSION),
    Dependency(Shell::class, BURROW_VERSION),
    Dependency(HoardPair::class, BURROW_VERSION),
    Dependency(HoardTime::class, BURROW_VERSION),
    Dependency(HoardTag::class, BURROW_VERSION)
)
@InverseRegisterCommands
@InverseSetConfig(
    ConfigItem(
        HoardPair.ConfigKey.ALLOW_DUPLICATE_KEYS,
        value = "false",
        type = ConverterPairType.BOOLEAN
    ),
    ConfigItem(HoardPair.ConfigKey.KEY_NAME, value ="name"),
    ConfigItem(HoardPair.ConfigKey.VALUE_NAME, value = "url"),
    ConfigItem(
        Web.ConfigKey.SILENTLY_DISPATCH,
        defaultValue = "false",
        type = ConverterPairType.BOOLEAN
    ),
)
class Web(renovator: Renovator) : Furnishing(renovator) {
    override fun launch() {
        use(Shell::class).apply {
            createShellFileIfNotExist()
        }
    }

    object ConfigKey {
        const val SILENTLY_DISPATCH = "web.silently_dispatch"
    }
}