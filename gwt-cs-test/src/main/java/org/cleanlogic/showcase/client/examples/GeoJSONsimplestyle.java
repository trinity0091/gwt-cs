/*
 * Copyright 2016 iserge.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.cleanlogic.showcase.client.examples;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;
import org.cesiumjs.cs.Configuration;
import org.cesiumjs.cs.datasources.DataSource;
import org.cesiumjs.cs.datasources.GeoJsonDataSource;
import org.cesiumjs.cs.promise.Fulfill;
import org.cesiumjs.cs.promise.Promise;
import org.cesiumjs.cs.scene.enums.SceneMode;
import org.cesiumjs.cs.widgets.Viewer;
import org.cesiumjs.cs.widgets.ViewerPanelAbstract;
import org.cesiumjs.cs.widgets.options.ViewerOptions;
import org.cleanlogic.showcase.client.basic.AbstractExample;
import org.cleanlogic.showcase.client.components.store.ShowcaseExampleStore;

import javax.inject.Inject;

/**
 * @author Serge Silaev aka iSergio <s.serge.b@gmail.com>
 */
public class GeoJSONsimplestyle extends AbstractExample {
    private class ViewerPanel implements IsWidget {
        private ViewerPanelAbstract _csPanelAbstract;

        private ViewerPanel() {
            super();
            asWidget();
        }

        @Override
        public Widget asWidget() {
            if (_csPanelAbstract == null) {
                final Configuration csConfiguration = new Configuration();
                csConfiguration.setPath(GWT.getModuleBaseURL() + "JavaScript/Cesium");
                _csPanelAbstract = new ViewerPanelAbstract(csConfiguration) {
                    @Override
                    public Viewer createViewer(Element element) {
                        ViewerOptions viewerOptions = new ViewerOptions();
                        viewerOptions.sceneMode = SceneMode.SCENE2D();
                        viewerOptions.timeline = false;
                        viewerOptions.animation = false;
                        _viewer = new Viewer(element, viewerOptions);
                        Promise<GeoJsonDataSource, String> dataSource = GeoJsonDataSource.load(GWT.getModuleBaseURL() + "SampleData/simplestyles.geojson");
                        _viewer.dataSources().add(dataSource);
                        dataSource.then(new Fulfill<GeoJsonDataSource>() {
                            @Override
                            public void onFulfilled(GeoJsonDataSource value) {
                                _viewer.zoomTo(value);
                            }
                        });

                        return _viewer;
                    }
                };
            }
            return _csPanelAbstract;
        }
    }

    @Inject
    public GeoJSONsimplestyle(ShowcaseExampleStore store) {
        super("GeoJSON simplestyle", "Load GeoJSON with simplestyle information", new String[]{"Showcase", "Cesium", "3d", "Viewer"}, store);
    }

    @Override
    public void buildPanel() {
        ViewerPanel csVPanel = new ViewerPanel();

        contentPanel.add(new HTML("<p>Load GeoJSON with simplestyle information.</p>"));
        contentPanel.add(csVPanel);

        initWidget(contentPanel);
    }

    @Override
    public String[] getSourceCodeURLs() {
        String[] sourceCodeURLs = new String[1];
        sourceCodeURLs[0] = GWT.getModuleBaseURL() + "examples/" + "GeoJSONsimplestyle.txt";
        return sourceCodeURLs;
    }
}