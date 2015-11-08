package Trubby.co.th.Particle;

import org.bukkit.Location;
import org.bukkit.util.Vector;

public class CircleEffect {
	public double xRotation;
	public double yRotation;
	public double zRotation = 0.0D;
	public double angularVelocityX = 0D;
	public double angularVelocityY = 0D;
	public double angularVelocityZ = 0D;
	public float radius = 0.8F;
	protected float step = 0.0F;
	public double xSubtract;
	public double ySubtract;
	public double zSubtract;
	public boolean enableRotation = true;
	public int particles = 16;
	
	public CircleEffect(float radius, int amount) {
		this.radius = radius;
		this.particles = amount;
	}

	public void play(Location loc) {

		for (int i = 0; i < particles; i++) {
			
			Location location = loc.clone().add(0, 0, -(radius/15));
			location.subtract(this.xSubtract, this.ySubtract, this.zSubtract);
			double inc = 6.283185307179586D / this.particles;
			double angle = this.step * inc;
			Vector v = new Vector();
			v.setX(Math.cos(angle) * this.radius);
			v.setZ(Math.sin(angle) * this.radius);
			ParticleVectorUtils.rotateVector(v, this.xRotation, this.yRotation, this.zRotation);
			if (this.enableRotation) {
				ParticleVectorUtils.rotateVector(v, this.angularVelocityX * this.step, this.angularVelocityY * this.step, this.angularVelocityZ * this.step);
			}
			// display(this.particle, location.add(v), 0.0F, 30);
			ParticleEffect.FLAME.display(0f, 0f, 0f, 0f, 1, location.add(v), 20);
			this.step += 1.0F;
		
		}
	}
}
