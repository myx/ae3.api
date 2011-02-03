package ru.myx.ae3.exec;

import ru.myx.ae3.base.BaseObject;
import ru.myx.ae3.base.BasePrimitive;

interface ExecDirect<T> extends BasePrimitive<T> {
	BaseObject<?> toDetachable();
}
