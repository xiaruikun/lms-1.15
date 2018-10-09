package com.next

class ExternalDataReceiverMessage
{
    String code
    String message

    static belongsTo = [receiver: ExternalDataReceiver]

    static constraints = {
        code maxSize: 8, unique: ['receiver']
        message maxSize: 1024
    }
}
