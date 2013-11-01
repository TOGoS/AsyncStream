package togos.asyncstream;

public class BaseCharStreamSource<E extends Exception> extends BaseStreamSource<char[], E>
{
	protected Appendable getSourceOutputAsAppendable() {
		return new StreamAppendable(getSourceOutputAsDestination());
	}
}
