package togos.asyncstream;

public interface StreamSource<T, E>
{
	public void pipe( StreamDestination<? super T, ? extends E> dest );
}
