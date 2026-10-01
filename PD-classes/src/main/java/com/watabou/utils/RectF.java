
package com.watabou.utils;

public class RectF {

	public float left;
	public float top;
	public float right;
	public float bottom;

	public RectF() {
		this( 0, 0, 0, 0 );
	}

	public RectF( RectF rect ) {
		this( rect.left, rect.top, rect.right, rect.bottom );
	}

	public RectF( float left, float top, float right, float bottom ) {
		this.left	= left;
		this.top	= top;
		this.right	= right;
		this.bottom	= bottom;
	}

	public float width() {
		return right - left;
	}

	public float height() {
		return bottom - top;
	}

	public float square() {
		return (right - left) * (bottom - top);
	}

	public RectF set( float left, float top, float right, float bottom ) {
		this.left	= left;
		this.top	= top;
		this.right	= right;
		this.bottom	= bottom;
		return this;
	}

	public RectF set( RectF rect ) {
		return set( rect.left, rect.top, rect.right, rect.bottom );
	}

	public boolean isEmpty() {
		return right <= left || bottom <= top;
	}

	public RectF setEmpty() {
		left = right = top = bottom = 0;
		return this;
	}

	public boolean intersect( RectF other ) {
		float l = Math.max( left, other.left );
		float r = Math.min( right, other.right );
		float t = Math.max( top, other.top );
		float b = Math.min( bottom, other.bottom );
        if (l < r && t < b) {
            left = l;
            right = r;
            top = t;
            bottom = b;
            return true;
        }
		return false;
	}

	public RectF union( float x, float y ) {
		if (isEmpty()) {
			return set( x, y, x + 1, y + 1 );
		} else {
			if (x < left) {
				left = x;
			} else if (x >= right) {
				right = x + 1;
			}
			if (y < top) {
				top = y;
			} else if (y >= bottom) {
				bottom = y + 1;
			}
			return this;
		}
	}

	public RectF union( PointF p ) {
		return union( p.x, p.y );
	}

	public boolean inside( PointF p ) {
		return p.x >= left && p.x < right && p.y >= top && p.y < bottom;
	}

	public RectF offset( float dx, float dy ) {
		left += dx;
		right += dx;
		top += dy;
		bottom += dy;
		return this;
	}
}
