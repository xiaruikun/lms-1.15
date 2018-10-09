package com.next

class TerritoryTeamImport
{
    Boolean notifyWhenStageChanges = false
    String notifyWhenStageChangesString

    Date startTime
    String startTimeString
    Date endTime
    String endTimeString
    //"YYYY-MM-DD"

    Territory territory
    String territoryExternalId
    User user
    String userExternalId

    String operation

    String status = 'Pendding'
    String description

    static constraints = {
        //        teamRole nullable: true, blank: true
        notifyWhenStageChanges nullable: true, blank: true
        startTime nullable: true, blank: true
        endTime nullable: true, blank: true
        territory nullable: true, blank: true
        user nullable: true, blank: true
        description nullable: true, blank: true, maxSize: 256

        status inList: ['Pending', 'Error', 'Successful']
        operation inList: ['Append', 'Delete']
    }
}
