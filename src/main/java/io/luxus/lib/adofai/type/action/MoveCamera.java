package io.luxus.lib.adofai.type.action;

import io.luxus.lib.adofai.type.*;
import io.luxus.lib.adofai.util.ListUtil;
import lombok.Getter;
import lombok.ToString;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Getter
@ToString
public class MoveCamera extends Action {

	private final Double duration;
	private final CameraRelativeTo relativeTo;
	private final List<Double> position;
	private final Double rotation;
	private final Long zoom;
	private final Double angleOffset;
	private final Ease ease;
	private final String eventTag;

	private MoveCamera(Double duration, CameraRelativeTo relativeTo, List<Double> position, Double rotation, Long zoom, Double angleOffset, Ease ease, String eventTag) {
		super(EventType.MOVE_CAMERA);
		this.duration = duration;
		this.relativeTo = relativeTo;
		this.position = position;
		this.rotation = rotation;
		this.zoom = zoom;
		this.angleOffset = angleOffset;
		this.ease = ease;
		this.eventTag = eventTag;
	}

	@Getter
	@ToString
	public static final class Builder extends Action.Builder<Builder> {

		private Double duration = 1.0;
		private CameraRelativeTo relativeTo = CameraRelativeTo.PLAYER;
		private List<Double> position = Arrays.asList(0.0, 0.0);
		private Double rotation = 0.0;
		private Long zoom = 100L;
		private Double angleOffset = 0.0;
		private Ease ease = Ease.LINEAR;
		private String eventTag = "";

		/**
		 * set all parameter with given action
		 *
		 * @param src source action
		 * @return self
		 */
		public Builder from(MoveCamera src) {
			return self()
					.duration(src.duration)
					.relativeTo(src.relativeTo)
					.position(src.position)
					.rotation(src.rotation)
					.zoom(src.zoom)
					.angleOffset(src.angleOffset)
					.ease(src.ease)
					.eventTag(src.eventTag);
		}

		/**
		 * build AddText action with builder
		 *
		 * @return Built AddText action
		 */
		@Override
		public MoveCamera build() {
			return new MoveCamera(duration, relativeTo, position, rotation, zoom, angleOffset, ease, eventTag);
		}

		/**
		 * return self
		 *
		 * @return self
		 */
		@Override
		public Builder self() {
			return this;
		}

		/**
		 * setter of duration
		 *
		 * @param duration duration of MoveCamera Event
		 * @return self
		 * @throws NullPointerException when duration is null
		 */
		public Builder duration(Double duration) {
			Objects.requireNonNull(duration);
			this.duration = duration;
			return self();
		}

		/**
		 * setter of relativeTo
		 *
		 * @param relativeTo relativeTo of MoveCamera Event
		 * @return self
		 * @throws NullPointerException when relativeTo is null
		 */
		public Builder relativeTo(CameraRelativeTo relativeTo) {
			Objects.requireNonNull(relativeTo);
			this.relativeTo = relativeTo;
			return self();
		}

		/**
		 * setter of position
		 *
		 * @param position position of MoveCamera Event
		 * @return self
		 * @throws NullPointerException when position is null
		 * @throws IllegalArgumentException when size of position is not 2
		 */
		public Builder position(List<Double> position) {
			Objects.requireNonNull(position);
			if (position.size() != 2) {
				throw new IllegalArgumentException("size of position must be 2");
			}
			this.position = ListUtil.createNewUnmodifiableList(position);
			return self();
		}

		/**
		 * setter of rotation
		 *
		 * @param rotation rotation of MoveCamera Event
		 * @return self
		 * @throws NullPointerException when rotation is null
		 */
		public Builder rotation(Double rotation) {
			Objects.requireNonNull(rotation);
			this.rotation = rotation;
			return self();
		}

		/**
		 * setter of zoom
		 *
		 * @param zoom zoom of MoveCamera Event
		 * @return self
		 * @throws NullPointerException when 'zoom' is null
		 */
		public Builder zoom(Long zoom) {
			Objects.requireNonNull(zoom);
			this.zoom = zoom;
			return self();
		}

		/**
		 * setter of angleOffset
		 *
		 * @param angleOffset angleOffset of MoveCamera Event
		 * @return self
		 * @throws NullPointerException when angleOffset is null
		 */
		public Builder angleOffset(Double angleOffset) {
			Objects.requireNonNull(angleOffset);
			this.angleOffset = angleOffset;
			return self();
		}

		/**
		 * setter of ease
		 *
		 * @param ease ease of MoveCamera Event
		 * @return self
		 * @throws NullPointerException when ease is null
		 */
		public Builder ease(Ease ease) {
			Objects.requireNonNull(ease);
			this.ease = ease;
			return self();
		}

		/**
		 * setter of eventTag
		 *
		 * @param eventTag eventTag of MoveCamera Event
		 * @return self
		 * @throws NullPointerException when eventTag is null
		 */
		public Builder eventTag(String eventTag) {
			Objects.requireNonNull(eventTag);
			this.eventTag = eventTag;
			return self();
		}
	}

}
