package jp.kaiz.atsassistmod.utils;

public class ComparisonManager {
	public interface ComparisonBase<T> {
		java.lang.String getName();

		boolean isTrue(T o0, Object o1);

		T parseT(java.lang.String str);
	}

	public enum Integer implements ComparisonBase<java.lang.Integer> {
		EQUAL("==") {
			@Override
			public boolean isTrue(java.lang.Integer o0, Object o1) {
				return o0.equals(o1);
			}
		},
		GREATER_THAN(">") {
			@Override
			public boolean isTrue(java.lang.Integer o0, Object o1) {
				return o0 > (java.lang.Integer) o1;
			}
		},
		GREATER_EQUAL(">=") {
			@Override
			public boolean isTrue(java.lang.Integer o0, Object o1) {
				return o0 >= (java.lang.Integer) o1;
			}
		},
		LESS_THAN("<") {
			@Override
			public boolean isTrue(java.lang.Integer o0, Object o1) {
				return o0 < (java.lang.Integer) o1;
			}
		},
		LESS_EQUAL("<=") {
			@Override
			public boolean isTrue(java.lang.Integer o0, Object o1) {
				return o0 <= (java.lang.Integer) o1;
			}
		},
		NOT_EQUAL("!=") {
			@Override
			public boolean isTrue(java.lang.Integer o0, Object o1) {
				return !o0.equals(o1);
			}
		};
		private final java.lang.String name;

		Integer(java.lang.String name) {
			this.name = name;
		}

		@Override
		public java.lang.String getName() {
			return this.name;
		}

		@Override
		public abstract boolean isTrue(java.lang.Integer o0, Object o1);

		@Override
		public java.lang.Integer parseT(java.lang.String str) {
			try {
				return java.lang.Integer.parseInt(str);
			} catch (Exception e) {
				return 0;
			}
		}


	}

	public enum Double implements ComparisonBase<java.lang.Double> {
		EQUAL("==") {
			@Override
			public boolean isTrue(java.lang.Double o0, Object o1) {
				return o0.equals(o1);
			}
		},
		GREATER_THAN(">") {
			@Override
			public boolean isTrue(java.lang.Double o0, Object o1) {
				return o0 > (java.lang.Double) o1;
			}
		},
		GREATER_EQUAL(">=") {
			@Override
			public boolean isTrue(java.lang.Double o0, Object o1) {
				return o0 >= (java.lang.Double) o1;
			}
		},
		LESS_THAN("<") {
			@Override
			public boolean isTrue(java.lang.Double o0, Object o1) {
				return o0 < (java.lang.Double) o1;
			}
		},
		LESS_EQUAL("<=") {
			@Override
			public boolean isTrue(java.lang.Double o0, Object o1) {
				return o0 <= (java.lang.Double) o1;
			}
		},
		NOT_EQUAL("!=") {
			@Override
			public boolean isTrue(java.lang.Double o0, Object o1) {
				return !(o0.equals(o1));
			}
		};
		private final java.lang.String name;

		Double(java.lang.String name) {
			this.name = name;
		}

		@Override
		public java.lang.String getName() {
			return this.name;
		}

		@Override
		public abstract boolean isTrue(java.lang.Double o0, Object o1);

		@Override
		public java.lang.Double parseT(java.lang.String str) {
			try {
				return java.lang.Double.parseDouble(str);
			} catch (Exception e) {
				return 0d;
			}
		}
	}


	public enum String implements ComparisonBase<java.lang.String> {
		EQUAL("==") {
			@Override
			public boolean isTrue(java.lang.String o0, Object o1) {
				return o0.equals(o1);
			}
		},
		NOT_EQUAL("!=") {
			@Override
			public boolean isTrue(java.lang.String o0, Object o1) {
				return !o0.equals(o1);
			}
		},
		CONTAINS(" contains ") {
			@Override
			public boolean isTrue(java.lang.String o0, Object o1) {
				return o0.contains((java.lang.String) o1);
			}
		},
		NOT_CONTAINS(" !contains ") {
			@Override
			public boolean isTrue(java.lang.String o0, Object o1) {
				return !o0.contains((java.lang.String) o1);
			}
		};
		private final java.lang.String name;

		String(java.lang.String name) {
			this.name = name;
		}

		@Override
		public abstract boolean isTrue(java.lang.String o0, Object o1);

		@Override
		public java.lang.String getName() {
			return this.name;
		}

		@Override
		public java.lang.String parseT(java.lang.String str) {
			return str;
		}
	}

	public enum Vec3 implements ComparisonBase<jp.ngt.ngtlib.math.Vec3> {
		EQUAL("==") {
			@Override
			public boolean isTrue(jp.ngt.ngtlib.math.Vec3 o0, Object o1) {
				return o0.equals(o1);
			}
		},
		NOT_EQUAL("!=") {
			@Override
			public boolean isTrue(jp.ngt.ngtlib.math.Vec3 o0, Object o1) {
				return !o0.equals(o1);
			}
		};
		private final java.lang.String name;

		Vec3(java.lang.String name) {
			this.name = name;
		}

		@Override
		public abstract boolean isTrue(jp.ngt.ngtlib.math.Vec3 o0, Object o1);

		@Override
		public java.lang.String getName() {
			return this.name;
		}

		@Override
		public jp.ngt.ngtlib.math.Vec3 parseT(java.lang.String str) {
			try {
				java.lang.String[] vecs = str.split(",");
				return new jp.ngt.ngtlib.math.Vec3(java.lang.Double.parseDouble(vecs[0]), java.lang.Double.parseDouble(vecs[1]), java.lang.Double.parseDouble(vecs[2]));
			} catch (Exception e) {
				return new jp.ngt.ngtlib.math.Vec3(0, 0, 0);
			}
		}
	}

	public enum Boolean implements ComparisonBase<java.lang.Boolean> {
		TRUE("==True") {
			@Override
			public boolean isTrue(java.lang.Boolean o0, Object o1) {
				return o0;
			}
		},
		FALSE("==False") {
			@Override
			public boolean isTrue(java.lang.Boolean o0, Object o1) {
				return !o0;
			}
		};
		private final java.lang.String name;

		Boolean(java.lang.String name) {
			this.name = name;
		}

		@Override
		public abstract boolean isTrue(java.lang.Boolean o0, Object o1);

		@Override
		public java.lang.String getName() {
			return this.name;
		}

		@Override
		public java.lang.Boolean parseT(java.lang.String str) {
			try {
				return java.lang.Boolean.parseBoolean(str);
			} catch (Exception e) {
				return false;
			}
		}
	}
}
