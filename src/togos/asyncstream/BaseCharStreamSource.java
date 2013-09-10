package togos.asyncstream;

public class BaseCharStreamSource extends BaseStreamSource<char[]>
{
	protected Appendable getSourceOutputAsAppendable() {
		return new StreamAppendable(getSourceOutputAsDestination());
	}
}
