package io.luxus.lib.adofai.type.action;

import io.luxus.lib.adofai.type.Ease;
import io.luxus.lib.adofai.type.EventType;
import lombok.Getter;
import lombok.ToString;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static io.luxus.lib.adofai.util.ListUtil.toUnmodifiableXYList;


@Getter
@ToString
public class FreeRoam extends Action {

	private final Double duration;
	private final List<Double> size;
	private final List<Double> positionOffset;
	private final Long outTime;
	private final Ease outEase;
	private final Long countdownTicks;
	private final Long angleCorrectionDir;

	private FreeRoam(Boolean active, Double duration, List<Double> size, List<Double> positionOffset, Long outTime, Ease outEase, Long countdownTicks, Long angleCorrectionDir) {
		super(EventType.FREE_ROAM, active);
		this.duration = duration;
		this.size = size;
		this.positionOffset = positionOffset;
		this.outTime = outTime;
		this.outEase = outEase;
		this.countdownTicks = countdownTicks;
		this.angleCorrectionDir = angleCorrectionDir;
	}

	@Getter
	@ToString
	public static final class Builder extends Action.Builder<Builder> {

		private Double duration = 16.0;
		private List<Double> size = Arrays.asList(4.0, 4.0);
		private List<Double> positionOffset = Arrays.asList(0.0, 0.0);
		private Long outTime = 4L;
		private Ease outEase = Ease.IN_OUT_SINE;
		private Long countdownTicks = 4L;
		private Long angleCorrectionDir = -1L;


		/**
		 * set all parameter with given action
		 *
		 * @param src source action
		 * @return self
		 */
		public Builder from(FreeRoam src) {
			return self()
					.duration(src.duration)
					.size(src.size)
					.positionOffset(src.positionOffset)
					.outTime(src.outTime)
					.outEase(src.outEase)
					.countdownTicks(src.countdownTicks)
					.angleCorrectionDir(src.angleCorrectionDir);
		}

		/**
		 * build AddText action with builder
		 *
		 * @return Built AddText action
		 */
		@Override
		public FreeRoam build() {
			return new FreeRoam(active, duration, size, positionOffset, outTime, outEase, countdownTicks, angleCorrectionDir);
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
		 * return eventType of Action Builder
		 *
		 * @return eventType
		 */
		@Override
		public EventType getEventType() {
			return EventType.FREE_ROAM;
		}

		public Builder duration(Double duration) {
			Objects.requireNonNull(duration);
			this.duration = duration;
			return this;
		}

		public Builder size(List<Double> size) {
			Objects.requireNonNull(size);
			if (size.size() != 2) {
				throw new IllegalArgumentException("size of size should be 2");
			}
			this.size = toUnmodifiableXYList(size);
			return this;
		}

		public Builder positionOffset(List<Double> positionOffset) {
			Objects.requireNonNull(positionOffset);
			if (positionOffset.size() != 2) {
				throw new IllegalArgumentException("size of positionOffset should be 2");
			}
			this.positionOffset = toUnmodifiableXYList(positionOffset);
			return this;
		}

		public Builder outTime(Long outTime) {
			Objects.requireNonNull(outTime);
			this.outTime = outTime;
			return this;
		}

		public Builder outEase(Ease outEase) {
			Objects.requireNonNull(outEase);
			this.outEase = outEase;
			return this;
		}

		public Builder countdownTicks(Long countdownTicks) {
			Objects.requireNonNull(countdownTicks);
			this.countdownTicks = countdownTicks;
			return this;
		}

		public Builder angleCorrectionDir(Long angleCorrectionDir) {
			Objects.requireNonNull(angleCorrectionDir);
			this.angleCorrectionDir = angleCorrectionDir;
			return this;
		}

	}

}
