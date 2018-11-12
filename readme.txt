java -jar avro-tools-1.8.2.jar tojson ./maddsp.tracking.1501236240000.chl.log.avro

java -jar /path/to/avro-tools-1.8.2.jar compile schema <schema file> <destination>
java -jar /path/to/avro-tools-1.8.2.jar compile schema user.avsc .

在Avro中序列化和反序列化分别由writer和reader完成，它们各自和一套schema相绑定。如果writer和reader的schema不同，即存在Schema Evolution，
是由Avro Parser根据一套预先定义好的resolution rules在writer和reader的schema层面解决的，如果解决不了就报错了，即双方schema不兼容。

为了保证schema前后兼容，在定义或变更avro schema时，需要注意以下几点：

    给所有field定义default值。如果某field没有default值，以后将不能删除该field；
    如果要新增field，必须定义default值；
    不能修改field type；
    不能修改field name，不过可以通过增加alias解决。

一些细节是：

    对于union类型，default值的类型只能是union的第一个类型；
    对于null类型，在定义的时候需要用引号，但使用的时候不行 -_-!

