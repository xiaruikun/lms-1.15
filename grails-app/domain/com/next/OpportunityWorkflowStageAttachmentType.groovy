package com.next

class OpportunityWorkflowStageAttachmentType
{
    static belongsTo = [attachmentType: AttachmentType, stage: OpportunityWorkflowStage]

    static constraints = {
        attachmentType unique: ['stage']
    }
}
