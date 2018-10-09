package com.next

class Chargeback
{
	Date date
	//�������
	String serialNumber
    String city
    //������
    String liablePerson
 	//�ſ���Խ���
 	String abutmentPerson
    //��Ѻƾ֤
    String detentionCredential
 	//ȱ��
 	String loses
 	//�ϴ���ȫ/������/����
 	String vagues
 	//¼�����/��ȫ
 	String defects
 	//��ͬ���ݴ���/ȱʧ
 	String contracts
 	//�������
 	String waifangs
    //ϵͳ����
    String systems
    //�˵�˵��
    String reason

    static constraints = {
        detentionCredential nullable: true, blank: true
        loses nullable: true, blank: true
        vagues nullable: true, blank: true
        defects nullable: true, blank: true
        contracts nullable: true, blank: true
        waifangs nullable: true, blank: true
        systems nullable: true, blank: true
    }
}