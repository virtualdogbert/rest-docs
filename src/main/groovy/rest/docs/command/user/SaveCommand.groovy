package rest.docs.command.user

import rest.docs.command.CommandTrait

class SaveCommand  implements CommandTrait {
    String  username
    String  password
    Boolean enabled         = true
    Boolean accountExpired  = false
    Boolean accountLocked   = false
    Boolean passwordExpired = false

    static constraints = {
        username size: 2..15
        password size: 4..15
    }

}
