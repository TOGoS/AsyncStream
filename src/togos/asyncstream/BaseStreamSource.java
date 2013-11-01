package togos.asyncstream;

import java.util.ArrayList;

public class BaseStreamSource<T, E extends Throwable> implements StreamSource<T, E>
{
	ArrayList<StreamDestination<? super T, ? extends E>> pipes = new ArrayList<StreamDestination<? super T, ? extends E>>();
	
	@Override public void pipe( StreamDestination<? super T, ? extends E> dest ) {
		pipes.add(dest);
	}
	
	public void _data( T value ) throws E {
		for( StreamDestination<? super T, ? extends E> dest : pipes ) {
			dest.data( value );
		}
	}
	
	public void _end() throws E {
		for( StreamDestination<? super T, ? extends E> dest : pipes ) {
			dest.end();
		}
	}
	
	/** Default implementation for StreamDestination<?> */
	public void end() throws E {
		_end();
	}
	
	/**
	 * Returns a destination stream that forwards
	 * to the ~outputs~ of this stream.
	 */
	protected StreamDestination<T, E> getSourceOutputAsDestination() {
		return new StreamDestination<T, E>() {
			@Override public void data(T value) throws E { _data(value); }
			@Override public void end() throws E { _end(); }
		};
	}
}
