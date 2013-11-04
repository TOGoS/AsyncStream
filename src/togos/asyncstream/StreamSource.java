package togos.asyncstream;

public interface StreamSource<T, E extends Throwable>
{
	public void pipe( StreamDestination<? super T, ? extends E> dest );
}
