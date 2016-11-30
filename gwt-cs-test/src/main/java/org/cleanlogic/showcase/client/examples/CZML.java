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
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.*;
import org.cesiumjs.cs.Configuration;
import org.cesiumjs.cs.core.Cartesian3;
import org.cesiumjs.cs.core.HeadingPitchRoll;
import org.cesiumjs.cs.core.Math;
import org.cesiumjs.cs.datasources.CzmlDataSource;
import org.cesiumjs.cs.scene.options.ViewOptions;
import org.cesiumjs.cs.widgets.Viewer;
import org.cesiumjs.cs.widgets.ViewerPanelAbstract;
import org.cleanlogic.showcase.client.basic.AbstractExample;
import org.cleanlogic.showcase.client.components.store.ShowcaseExampleStore;

import javax.inject.Inject;

/**
 * @author Serge Silaev aka iSergio <s.serge.b@gmail.com>
 */
public class CZML extends AbstractExample {
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
                        _viewer = new Viewer(element);
                        _viewer.dataSources().add(CzmlDataSource.load(GWT.getModuleBaseURL() + "SampleData/simple.czml"));
                        _viewer.camera.flyHome(0);
                        return _viewer;
                    }
                };
            }
            return _csPanelAbstract;
        }

        public void reset() {
            _csPanelAbstract.getViewer().dataSources().removeAll();
        }
    }

    @Inject
    public CZML(ShowcaseExampleStore store) {
        super("CZML", "CZML document load example", new String[]{"Showcase", "Cesium", "3d", "Viewer", "CZML"}, store);
    }

    @Override
    public void buildPanel() {
        final ViewerPanel csVPanel = new ViewerPanel();

        Button satellitesBtn = new Button("Satellites");
        satellitesBtn.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent clickEvent) {
                csVPanel.reset();
                csVPanel._csPanelAbstract.getViewer().dataSources().add(CzmlDataSource.load(GWT.getModuleBaseURL() + "SampleData/simple.czml"));
                csVPanel._csPanelAbstract.getViewer().camera.flyHome(0);
            }
        });

        Button vehicleBtn = new Button("Vehicle");
        vehicleBtn.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent clickEvent) {
                csVPanel.reset();
                csVPanel._csPanelAbstract.getViewer().dataSources().add(CzmlDataSource.load(GWT.getModuleBaseURL() + "SampleData/Vehicle.czml"));
                ViewOptions viewOptions = new ViewOptions();
                viewOptions.destinationPos = Cartesian3.fromDegrees(-116.52, 35.02, 95000);
                viewOptions.orientation = new HeadingPitchRoll(6, -Math.PI_OVER_TWO());
                csVPanel._csPanelAbstract.getViewer().scene().camera().setView(viewOptions);
            }
        });

        HorizontalPanel hPanel = new HorizontalPanel();
        hPanel.setSpacing(10);
        hPanel.add(satellitesBtn);
        hPanel.add(vehicleBtn);

        AbsolutePanel aPanel = new AbsolutePanel();
        aPanel.add(csVPanel);
        aPanel.add(hPanel, 20, 20);

        contentPanel.add(new HTML("<p>This example shows CZML document load example application</p>"));
        contentPanel.add(aPanel);

        initWidget(contentPanel);
    }

    @Override
    public String[] getSourceCodeURLs() {
        String[] sourceCodeURLs = new String[1];
        sourceCodeURLs[0] = GWT.getModuleBaseURL() + "examples/" + "CZML.txt";
        return sourceCodeURLs;
    }
}
