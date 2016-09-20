package style;

import android.util.AttributeSet;
import android.view.View;

public interface StyleableViewBinder<T extends View> {
    void bind(T target, AttributeSet attrs);
}
