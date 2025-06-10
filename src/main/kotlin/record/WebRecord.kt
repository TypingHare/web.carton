package burrow.carton.web.record

import burrow.carton.hoard.Record
import burrow.carton.hoard.record.Timed

open class WebRecord(): Record(), Timed {
    override var createdAt: Long? = null
    override var updatedAt: Long? = null
    var name: String = ""
    var url: String = ""
}