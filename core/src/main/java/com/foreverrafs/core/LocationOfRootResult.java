package com.foreverrafs.core;

import android.os.Parcel;
import android.os.Parcelable;

public class LocationOfRootResult implements Parcelable {
    private double x1, x2, x3, tolerance;  //for bisection and secante
    private double fx1, fx2, derX1;             //for newton raphson
    private double difference; //for secante;
    private double fx3; // for false position
    private int iteration;

    //Bisection Method Signature
    public LocationOfRootResult(double x1, double x2, double x3, int iteration, double tolerance) {
        this.x1 = x1;
        this.x2 = x2;
        this.x3 = x3;
        this.iteration = iteration;
        this.tolerance = tolerance;
    }

    //Newton Raphson Signature ::: x1 is what most users are interested in
    public LocationOfRootResult(double x1, int iteration, double fx1, double derX1) {
        this.x1 = x1;
        this.iteration = iteration;
        this.fx1 = fx1;
        this.derX1 = derX1;
    }

    //secante Signature::: x3 is what most users are interested in
    public LocationOfRootResult(int iteration, double x1, double x2, double x3, double difference) {
        this.iteration = iteration;
        this.x1 = x1;
        this.x2 = x2;
        this.x3 = x3;
        this.difference = difference;
    }

    //False Position signature :: x3 is what most users are interested in
    public LocationOfRootResult(int iteration, double x1, double x2, double x3, double fx1, double fx2, double fx3, double difference) {
        this.iteration = iteration;
        this.x1 = x1;
        this.x2 = x2;
        this.x3 = x3;
        this.fx1 = fx1;
        this.fx2 = fx2;
        this.fx3 = fx3;
        this.difference = difference;
    }

    public static final Creator<LocationOfRootResult> CREATOR = new Creator<LocationOfRootResult>() {
        @Override
        public LocationOfRootResult createFromParcel(Parcel in) {
            return new LocationOfRootResult(in);
        }

        @Override
        public LocationOfRootResult[] newArray(int size) {
            return new LocationOfRootResult[size];
        }
    };

    protected LocationOfRootResult(Parcel in) {
        x1 = in.readDouble();
        x2 = in.readDouble();
        x3 = in.readDouble();
        tolerance = in.readDouble();
        fx1 = in.readDouble();
        fx2 = in.readDouble();
        derX1 = in.readDouble();
        difference = in.readDouble();
        fx3 = in.readDouble();
        iteration = in.readInt();
    }

    public double getX1() {
        return x1;
    }

    public double getDerX1() {
        return derX1;
    }

    public double getX2() {
        return x2;
    }

    public double getFx1() {
        return fx1;
    }

    public double getFx2() {
        return fx2;
    }

    public double getFx3() {
        return fx3;
    }

    public double getDifference() {
        return difference;
    }


    public double getX3() {
        return x3;
    }

    public double getTolerance() {
        return tolerance;
    }

    public int getIteration() {
        return iteration;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeDouble(x1);
        parcel.writeDouble(x2);
        parcel.writeDouble(x3);
        parcel.writeDouble(tolerance);
        parcel.writeDouble(fx1);
        parcel.writeDouble(fx2);
        parcel.writeDouble(derX1);
        parcel.writeDouble(difference);
        parcel.writeDouble(fx3);
        parcel.writeInt(iteration);
    }
}

