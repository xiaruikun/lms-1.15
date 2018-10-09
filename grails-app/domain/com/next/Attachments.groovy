package com.next

class Attachments
{
    AttachmentType type
    String fileName
    String fileUrl
    String thumbnailUrl

    String description

    Date createdDate = new Date()
    Date modifiedDate = new Date()

    Integer displayOrder = 0

    User createBy
    User modifyBy

    String externalId

    static belongsTo = [opportunity: Opportunity, activity: Activity, contact: Contact]

    static constraints = {
        // fileName nullable: true, blank: true
        description nullable: true, blank: true, maxSize: 1024
        opportunity nullable: true, blank: true
        activity nullable: true, blank: true
        contact nullable: true, blank: true
        fileUrl maxSize: 512, nullable: true, blank: true
        createBy nullable: true, blank: true
        modifyBy nullable: true, blank: true
        externalId maxSize: 32, blank: true, nullable: true
        thumbnailUrl maxSize: 512, nullable: true, blank: true
    }

    def beforeInsert()
    {
        if (!fileUrl)
        {
            fileUrl = fileName
        }
    }
}
