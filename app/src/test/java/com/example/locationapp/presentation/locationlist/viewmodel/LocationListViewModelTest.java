package com.example.locationapp.presentation.locationlist.viewmodel;


import com.example.locationapp.domain.interactor.GetPreferLocationsUseCase;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import javax.inject.Inject;

@RunWith(MockitoJUnitRunner.class)
public class LocationListViewModelTest {
    @Inject
    GetPreferLocationsUseCase getPreferLocationsUseCase;

    @Before
    public void setUp() {
    }

    @Test
    public void test() {
        Assert.assertEquals(1, 1 * 1);
    }
}