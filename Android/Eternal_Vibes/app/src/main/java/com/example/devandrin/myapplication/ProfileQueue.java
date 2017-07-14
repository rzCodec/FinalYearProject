package com.example.devandrin.myapplication;

/**
 * Created by Ronald on 7/14/2017.
 */

import java.util.ArrayList;
import java.util.Iterator;

public class ProfileQueue implements Iterable<RadarContent> {

    private RadarContent[] arrProfiles;
    private int length = 0;

    /**
     * Create a brand new Priority Queue to hold future profiles
     */
    public ProfileQueue() {
        this.arrProfiles = new RadarContent[0];
        this.length = 0;
    }

    /**
     * Create a new Priority Queue using an existing array of user profiles
     * @param arrProfiles - Array of profiles obtained from the API response
     */
    public ProfileQueue(RadarContent[] arrProfiles) {
        this.arrProfiles = arrProfiles;
        this.length = arrProfiles.length;
    }

    /**
     * Create a new Priority Queue using an array list and converting them to an array for optimal sort performance
     * @param radarContentList
     */
    public ProfileQueue(ArrayList<RadarContent> radarContentList){
        this.arrProfiles = new RadarContent[radarContentList.size()];
        radarContentList.toArray(arrProfiles);
        this.length = radarContentList.size();
    }

    /**
     * Invokes the relevant sorting functions based on user selection (Still need to adds this functionality)
     * @param sortType - Specifies what the Profiles will be sorted by
     * @param isAscending - Smallest to largest or vice versa
     */
    public void ProfileSort(String sortType, Boolean isAscending) {
        if(sortType.equals("DISTANCE")) {
            //Sort by distance
            quickSortDistance(arrProfiles, 0, arrProfiles.length - 1, isAscending);
        }
        else {
            //Sort by rating
            quickSortRating(arrProfiles, 0, arrProfiles.length - 1, isAscending);
        }
    }

    /**
     * Sort by Distance
     * @param arrProfiles
     * @param low - Start value
     * @param high - Max length of the array
     * @param isAscending - Sort by smallest to largest or vice versa
     */
    private void quickSortDistance(RadarContent[] arrProfiles, int low, int high, Boolean isAscending) {
        if (low >= high)
            return;

        // pick the pivot
        int middle = low + (high - low) / 2;
        int pivot = arrProfiles[middle].getDistance();

        // make left < pivot and right > pivot
        int i = low, j = high;
        while (i <= j) {

            if(isAscending == true) {
                while (arrProfiles[i].getDistance() < pivot) {
                    i++;
                }

                while (arrProfiles[j].getDistance() > pivot) {
                    j--;
                }
            }
            else //Sort by largest value to smallest
            {
                while (arrProfiles[i].getDistance() > pivot) {
                    i++;
                }

                while (arrProfiles[j].getDistance() < pivot) {
                    j--;
                }
            }

            if (i <= j) {
                //Swap elements
                int temp = arrProfiles[i].getDistance();
                arrProfiles[i].setDistance(arrProfiles[j].getDistance());
                arrProfiles[j].setDistance(temp);
                i++;
                j--;
            }
        }

        // recursively sort two sub parts of the array of profiles
        if (low < j)
            quickSortDistance(arrProfiles, low, j, isAscending);

        if (high > i)
            quickSortDistance(arrProfiles, i, high, isAscending);
    }

    /**
     * Sort by Rating
     * @param arrProfiles
     * @param low - Start value
     * @param high - Max length of the array
     * @param isAscending - Sort by smallest to largest or vice versa
     */
    private void quickSortRating(RadarContent[] arrProfiles, int low, int high, Boolean isAscending) {
        if (low >= high)
            return;

        // pick the pivot
        int middle = low + (high - low) / 2;
        int pivot = arrProfiles[middle].getRating();

        // make left < pivot and right > pivot
        int i = low, j = high;
        while (i <= j) {

            //Sort by smallest value to largest
            if(isAscending == true) {
                while (arrProfiles[i].getRating() < pivot) {
                    i++;
                }

                while (arrProfiles[j].getRating() > pivot) {
                    j--;
                }
            }
            else //Sort by largest value to smallest
            {
                while (arrProfiles[i].getRating() > pivot) {
                    i++;
                }

                while (arrProfiles[j].getRating() < pivot) {
                    j--;
                }
            }

            if (i <= j) {
                //Swap elements
                int temp = arrProfiles[i].getRating();
                arrProfiles[i].setRating(arrProfiles[j].getRating());
                arrProfiles[j].setRating(temp);
                i++;
                j--;
            }
        }

        // recursively sort two sub parts of the array of profiles
        if (low < j)
            quickSortRating(arrProfiles, low, j, isAscending);

        if (high > i)
            quickSortRating(arrProfiles, i, high, isAscending);
    }

    /**
     * Shift the elements in the array and decrease its size after each removal of a profile
     */
    private void shiftElements() {
        RadarContent[] tempArr = new RadarContent[length - 1];
        for(int i = 0; i < length - 1; i++) {
            tempArr[i] = arrProfiles[i + 1];
        }
        arrProfiles = tempArr;
        length--;
    }

    /**
     * @return and remove the Profile with the highest priority from the Priorty Queue
     */
    public RadarContent poll() {
        RadarContent p = arrProfiles[0];
        shiftElements();
        return p;
    }

    /**
     * @return the Profile with the highest priority without removing it from the Priority Queue
     */
    public RadarContent peek() {
        return arrProfiles[0];
    }

    private void expandArray() {
        RadarContent[] expandedArray = new RadarContent[this.length + 1];

        for(int i = 0; i < length; i++) {
            expandedArray[i] = arrProfiles[i];
        }

        arrProfiles = expandedArray;
        length++;
    }

    /**
     * Expands the array and adds a Profile to end of the Priority Queue
     * @param p - A profile
     */
    public void add(RadarContent p) {
        expandArray();
        arrProfiles[length - 1] = p;
    }

    /**
     * @return check if the Priority Queue is empty
     */
    public Boolean isEmpty() {
        return length == 0;
    }

    /**
     * Iterator to go through each Profile for this data structure
     */
    @Override
    public Iterator iterator() {

        Iterator<RadarContent> iterator = new Iterator<RadarContent>() {

            ProfileQueue pq = new ProfileQueue(arrProfiles);
            int iCursor;

            /*
             * Check for next available profile
             */
            @Override
            public boolean hasNext() {
                return !pq.isEmpty() && pq.arrProfiles[iCursor++] != null;
            }

            /**
             * Return the next profile and advance the cursor
             */
            @Override
            public RadarContent next() {
                RadarContent p = pq.arrProfiles[iCursor];
                iCursor++;
                return p;
            }
        };

        return iterator;

    } //end of method

} //end of class
