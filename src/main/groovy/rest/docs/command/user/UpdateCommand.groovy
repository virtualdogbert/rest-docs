package rest.docs.command.user

import rest.docs.command.CommandTrait

class UpdateCommand  implements CommandTrait {
    String  username
    String  password
    Boolean enabled
    Boolean accountExpired
    Boolean accountLocked
    Boolean passwordExpired

    static constraints = {
        username nullable: true, size: 2..15
        password nullable: true, size: 4..15
        enabled nullable: true
        accountExpired nullable: true
        accountLocked nullable: true
        passwordExpired nullable: true
    }

}
