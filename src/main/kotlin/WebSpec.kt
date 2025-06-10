package burrow.carton.web

import burrow.carton.cradle.Cradle
import burrow.carton.hoard.HoardPair
import burrow.carton.hoard.HoardTime
import burrow.carton.shell.Shell
import burrow.kernel.chamber.Dependency
import burrow.kernel.chamber.Spec

data class WebSpec(
    override var requires: Collection<Dependency> = listOf(
        Dependency(HoardPair::class, REQUIRED_HOARD_VERSION),
        Dependency(HoardTime::class, REQUIRED_HOARD_VERSION),
        Dependency(Cradle::class, REQUIRED_HOARD_VERSION),
        Dependency(Shell::class, REQUIRED_HOARD_VERSION),
    ),
    var autoDispatch: Boolean = true,
    var silentlyDispatch: Boolean = true
) : Spec(requires)