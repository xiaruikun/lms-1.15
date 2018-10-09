package com.next

import grails.transaction.Transactional
import sun.misc.BASE64Decoder

@Transactional
// @CompileStatic
//@TypeChecked
class AttachmentsService
{
    static scope = "singleton"

    def writeImage(Attachments attachment, String webrootDir)
    {
        def sImage = attachment['data']
        BASE64Decoder decoder = new BASE64Decoder();
        byte[] bImage = decoder.decodeBuffer(sImage);

        File fileImage = new File(webrootDir, "files/images/${attachment.fileName}.jpg")

        fileImage.append(bImage)
    }
}
