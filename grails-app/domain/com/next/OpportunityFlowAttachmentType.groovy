package com.next

class OpportunityFlowAttachmentType
{
    static belongsTo = [attachmentType: AttachmentType, stage: OpportunityFlow]

    static constraints = {
        attachmentType unique: ['stage']
    }
}
