java -jar avro-tools-1.8.2.jar tojson ./maddsp.tracking.1501236240000.chl.log.avro

java -jar /path/to/avro-tools-1.8.2.jar compile schema <schema file> <destination>
java -jar /path/to/avro-tools-1.8.2.jar compile schema user.avsc .

��Avro�����л��ͷ����л��ֱ���writer��reader��ɣ����Ǹ��Ժ�һ��schema��󶨡����writer��reader��schema��ͬ��������Schema Evolution��
����Avro Parser����һ��Ԥ�ȶ���õ�resolution rules��writer��reader��schema�������ģ����������˾ͱ����ˣ���˫��schema�����ݡ�

Ϊ�˱�֤schemaǰ����ݣ��ڶ������avro schemaʱ����Ҫע�����¼��㣺

    ������field����defaultֵ�����ĳfieldû��defaultֵ���Ժ󽫲���ɾ����field��
    ���Ҫ����field�����붨��defaultֵ��
    �����޸�field type��
    �����޸�field name����������ͨ������alias�����

һЩϸ���ǣ�

    ����union���ͣ�defaultֵ������ֻ����union�ĵ�һ�����ͣ�
    ����null���ͣ��ڶ����ʱ����Ҫ�����ţ���ʹ�õ�ʱ���� -_-!

