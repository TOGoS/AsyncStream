package togos.asyncstream;

public class StreamFork<T,E extends Throwable> extends BaseStreamSource<T,E> implements StreamDestination<T,E>
{
	@Override public void data( T value ) throws E {
		_data( value );
    }

	@Override public void end() throws E {
		_end();
    }
}
