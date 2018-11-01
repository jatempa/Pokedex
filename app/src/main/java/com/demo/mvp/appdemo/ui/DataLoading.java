package com.demo.mvp.appdemo.ui;

/**
 * Permite saber si un elemento relacionado con datos está cargandolos o si no posee más
 */
interface DataLoading {
    boolean isLoadingData();
    boolean isThereMoreData();
}
