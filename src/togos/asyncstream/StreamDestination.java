package togos.asyncstream;

public interface StreamDestination<T,E extends Throwable>
{
	public void data( T value ) throws E;
	public void end() throws E;
}
