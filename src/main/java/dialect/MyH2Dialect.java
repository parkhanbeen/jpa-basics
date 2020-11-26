package dialect;

import org.hibernate.dialect.H2Dialect;
import org.hibernate.dialect.function.StandardSQLFunction;
import org.hibernate.type.StandardBasicTypes;

public class MyH2Dialect extends H2Dialect {

    public MyH2Dialect() {
        registerFunction("grop_concat", new StandardSQLFunction("grop_concat", StandardBasicTypes.STRING));
    }
}
