package togos.asyncstream;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.io.Writer;
import java.util.Arrays;

public final class StreamUtil
{
	private StreamUtil() { }
	
	public static <E extends Throwable> void pipe( InputStream r, StreamDestination<byte[],E> d, boolean closeOnEnd ) throws IOException, E {
		byte[] buffer = new byte[1024];
		int i;
		while( (i = r.read(buffer)) > 0 ) {
			d.data( Arrays.copyOf(buffer, i) );
		}
		if( closeOnEnd ) d.end();
	}
	
	public static <E extends Throwable> void pipe( Reader r, StreamDestination<char[],E> d, boolean closeOnEnd ) throws IOException, E {
		char[] buffer = new char[1024];
		int i;
		while( (i = r.read(buffer)) > 0 ) {
			d.data( Arrays.copyOf(buffer, i) );
		}
		if( closeOnEnd ) d.end();
	}
	
	public static void pipe( StreamSource<char[], ? super IOException> source, final Writer w, final boolean closeOnEnd ) {
		source.pipe( new StreamDestination<char[], IOException>() {
			@Override public void data(char[] value) throws IOException {
				w.write(value);
			}
			@Override public void end() throws IOException {
				if( closeOnEnd ) w.close();
			}
		});
	}
	
	public static <T> void closeOnEnd( StreamSource<T, ? super IOException> source, final Closeable closeable ) {
		source.pipe( new StreamDestination<T, IOException>() {
			@Override public void data(T value) {};
			@Override public void end() throws IOException {
				closeable.close();
			}
		} );
	}
}
