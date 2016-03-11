package style;

import android.content.Context;
import android.util.AttributeSet;

public interface StyleableViewBinder<T> {
    void style(Context context, AttributeSet attrs, T target);
}
