package colm.internal

import com.sun.jna.Pointer
import com.sun.jna.PointerType

class OlmInboundGroupSession : PointerType {
    constructor(address: Pointer?) : super(address)
    constructor() : super()
}
