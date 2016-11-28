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
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.user.client.ui.*;
import org.cesiumjs.cs.Cesium;
import org.cesiumjs.cs.Configuration;
import org.cesiumjs.cs.core.*;
import org.cesiumjs.cs.core.Math;
import org.cesiumjs.cs.datasources.Entity;
import org.cesiumjs.cs.datasources.graphics.BillboardGraphics;
import org.cesiumjs.cs.datasources.graphics.options.BillboardGraphicsOptions;
import org.cesiumjs.cs.datasources.options.EntityOptions;
import org.cesiumjs.cs.datasources.properties.ConstantPositionProperty;
import org.cesiumjs.cs.datasources.properties.ConstantProperty;
import org.cesiumjs.cs.js.JsImage;
import org.cesiumjs.cs.promise.Fulfill;
import org.cesiumjs.cs.promise.Reject;
import org.cesiumjs.cs.scene.HorizontalOrigin;
import org.cesiumjs.cs.scene.VerticalOrigin;
import org.cesiumjs.cs.widgets.Viewer;
import org.cesiumjs.cs.widgets.ViewerPanelAbstract;
import org.cleanlogic.showcase.client.basic.AbstractExample;
import org.cleanlogic.showcase.client.components.store.ShowcaseExampleStore;

import javax.inject.Inject;
import java.util.logging.Logger;

/**
 * @author Serge Silaev aka iSergio <s.serge.b@gmail.com>
 */
public class Billboards extends AbstractExample {
    private static final Logger LOGGER = Logger.getLogger(Billboards.class.getName());
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
                        Viewer csViewer = new Viewer(element);

                        BillboardGraphicsOptions billboardGraphicsOptions = new BillboardGraphicsOptions();
                        billboardGraphicsOptions.image = new ConstantProperty<>(GWT.getModuleBaseURL() + "images/Cesium_Logo_overlay.png");
                        BillboardGraphics billboardGraphics = new BillboardGraphics(billboardGraphicsOptions);
                        EntityOptions entityOptions = new EntityOptions();
                        entityOptions.position = new ConstantPositionProperty(Cartesian3.fromDegrees(-75.5977, 40.03883));
                        entityOptions.billboard = billboardGraphics;
                        csViewer.entities().add(new Entity(entityOptions));
                        return csViewer;
                    }
                };
            }
            return _csPanelAbstract;
        }

        private Viewer getViewer() {
            return _csPanelAbstract.getViewer();
        }

        private void addBillboard() {
            BillboardGraphicsOptions billboardGraphicsOptions = new BillboardGraphicsOptions();
            billboardGraphicsOptions.image = new ConstantProperty<>(GWT.getModuleBaseURL() + "images/Cesium_Logo_overlay.png");
            BillboardGraphics billboardGraphics = new BillboardGraphics(billboardGraphicsOptions);
            EntityOptions entityOptions = new EntityOptions();
            entityOptions.position = new ConstantPositionProperty(Cartesian3.fromDegrees(-75.59777, 40.03883));
            entityOptions.billboard = billboardGraphics;
            _csPanelAbstract.getViewer().entities().add(new Entity(entityOptions));
        }

        private void setBillboardProperties() {
            BillboardGraphicsOptions billboardGraphicsOptions = new BillboardGraphicsOptions();
            billboardGraphicsOptions.image = new ConstantProperty<>(GWT.getModuleBaseURL() + "images/Cesium_Logo_overlay.png"); // default: undefined
            billboardGraphicsOptions.show = new ConstantProperty<>(true); // default
            billboardGraphicsOptions.pixelOffset = new ConstantProperty<>(new Cartesian2(0, -50)); // default: (0, 0)
            billboardGraphicsOptions.eyeOffset = new ConstantProperty<>(new Cartesian3(0, 0, 0)); // default
            billboardGraphicsOptions.horizontalOrigin = new ConstantProperty<>(HorizontalOrigin.CENTER());// default
            billboardGraphicsOptions.verticalOrigin = new ConstantProperty<>(VerticalOrigin.BOTTOM()); // default: CENTER
            billboardGraphicsOptions.scale = new ConstantProperty<>(2.0); // default: 1.0
            billboardGraphicsOptions.color = new ConstantProperty<>(Color.LIME()); // default: WHITE
            billboardGraphicsOptions.rotation = new ConstantProperty<>(Math.PI_OVER_FOUR()); // default: 0.0
            billboardGraphicsOptions.alignedAxis = new ConstantProperty<>(Cartesian3.ZERO()); // default
            billboardGraphicsOptions.width = new ConstantProperty<>(100); // default: undefined
            billboardGraphicsOptions.height = new ConstantProperty<>(25); // default: undefined
            EntityOptions entityOptions = new EntityOptions();
            entityOptions.position = new ConstantPositionProperty(Cartesian3.fromDegrees(-75.59777, 40.03883));
            entityOptions.billboard = new BillboardGraphics(billboardGraphicsOptions);
            _csPanelAbstract.getViewer().entities().add(new Entity(entityOptions));
        }

        private void changeBillboardProperties() {
            BillboardGraphicsOptions billboardGraphicsOptions = new BillboardGraphicsOptions();
            billboardGraphicsOptions.image = new ConstantProperty<>(GWT.getModuleBaseURL() + "images/Cesium_Logo_overlay.png");
            BillboardGraphics billboardGraphics = new BillboardGraphics(billboardGraphicsOptions);
            EntityOptions entityOptions = new EntityOptions();
            entityOptions.position = new ConstantPositionProperty(Cartesian3.fromDegrees(-75.59777, 40.03883, 300000.0));
            entityOptions.billboard = billboardGraphics;
            Entity entity = _csPanelAbstract.getViewer().entities().add(new Entity(entityOptions));
            entity.billboard.scale = new ConstantProperty<>(3.0);
            entity.billboard.color = new ConstantProperty<>(Color.WHITE().withAlpha(0.25f));
        }

        private void sizeBillboardInMeters() {
            BillboardGraphicsOptions billboardGraphicsOptions = new BillboardGraphicsOptions();
            billboardGraphicsOptions.image = new ConstantProperty<>(GWT.getModuleBaseURL() + "images/Cesium_Logo_overlay.png");
            billboardGraphicsOptions.sizeInMeters = new ConstantProperty<>(true);
            BillboardGraphics billboardGraphics = new BillboardGraphics(billboardGraphicsOptions);
            EntityOptions entityOptions = new EntityOptions();
            entityOptions.position = new ConstantPositionProperty(Cartesian3.fromDegrees(-75.59777, 40.03883));
            entityOptions.billboard = billboardGraphics;
            Entity entity = _csPanelAbstract.getViewer().entities().add(new Entity(entityOptions));
            getViewer().zoomTo(entity);
        }

        private void addMultipleBillboards() {
            String logoUrl = GWT.getModuleBaseURL() + "images/Cesium_Logo_overlay.png";
            String facilityUrl = GWT.getModuleBaseURL() + "images/facility.gif";

            BillboardGraphicsOptions billboardGraphicsOptions = new BillboardGraphicsOptions();
            billboardGraphicsOptions.image = new ConstantProperty<>(logoUrl);
            BillboardGraphics billboardGraphics = new BillboardGraphics(billboardGraphicsOptions);
            EntityOptions entityOptions = new EntityOptions();
            entityOptions.position = new ConstantPositionProperty(Cartesian3.fromDegrees(-75.59777, 40.03883));
            entityOptions.billboard = billboardGraphics;
            _csPanelAbstract.getViewer().entities().add(new Entity(entityOptions));

            billboardGraphicsOptions = new BillboardGraphicsOptions();
            billboardGraphicsOptions.image = new ConstantProperty<>(facilityUrl);
            billboardGraphics = new BillboardGraphics(billboardGraphicsOptions);
            entityOptions = new EntityOptions();
            entityOptions.position = new ConstantPositionProperty(Cartesian3.fromDegrees(-80.50, 35.14));
            entityOptions.billboard = billboardGraphics;
            _csPanelAbstract.getViewer().entities().add(new Entity(entityOptions));

            billboardGraphicsOptions = new BillboardGraphicsOptions();
            billboardGraphicsOptions.image = new ConstantProperty<>(facilityUrl);
            billboardGraphics = new BillboardGraphics(billboardGraphicsOptions);
            entityOptions = new EntityOptions();
            entityOptions.position = new ConstantPositionProperty(Cartesian3.fromDegrees(-80.12, 25.46));
            entityOptions.billboard = billboardGraphics;
            _csPanelAbstract.getViewer().entities().add(new Entity(entityOptions));
        }

        private void scaleByDistance() {
            BillboardGraphicsOptions billboardGraphicsOptions = new BillboardGraphicsOptions();
            billboardGraphicsOptions.image = new ConstantProperty<>(GWT.getModuleBaseURL() + "images/facility.gif");
            billboardGraphicsOptions.scaleByDistance = new ConstantProperty<>(new NearFarScalar(1.5e2, 2.0, 1.5e7, 0.5));
            BillboardGraphics billboardGraphics = new BillboardGraphics(billboardGraphicsOptions);
            EntityOptions entityOptions = new EntityOptions();
            entityOptions.position = new ConstantPositionProperty(Cartesian3.fromDegrees(-75.59777, 40.03883));
            entityOptions.billboard = billboardGraphics;
            _csPanelAbstract.getViewer().entities().add(new Entity(entityOptions));
        }

        private void fadeByDistance() {
            BillboardGraphicsOptions billboardGraphicsOptions = new BillboardGraphicsOptions();
            billboardGraphicsOptions.image = new ConstantProperty<>(GWT.getModuleBaseURL() + "images/Cesium_Logo_overlay.png");
            billboardGraphicsOptions.translucencyByDistance = new ConstantProperty<>(new NearFarScalar(1.5e2, 2.0, 1.5e7, 0.5));
            BillboardGraphics billboardGraphics = new BillboardGraphics(billboardGraphicsOptions);
            EntityOptions entityOptions = new EntityOptions();
            entityOptions.position = new ConstantPositionProperty(Cartesian3.fromDegrees(-75.59777, 40.03883));
            entityOptions.billboard = billboardGraphics;
            _csPanelAbstract.getViewer().entities().add(new Entity(entityOptions));
        }

        private void offsetByDistance() {
            Cesium.loadImage(GWT.getModuleBaseURL() + "images/Cesium_Logo_overlay.png").then(
                    new Fulfill<JsImage>() {
                        @Override
                        public void onFulfilled(final JsImage logoImg) {
                            Cesium.loadImage(GWT.getModuleBaseURL() + "images/facility.gif").then(
                                    new Fulfill<JsImage>() {
                                        @Override
                                        public void onFulfilled(JsImage facilityImg) {
                                            int facilityHeight = facilityImg.height;

                                            BillboardGraphicsOptions billboardGraphicsOptions = new BillboardGraphicsOptions();
                                            billboardGraphicsOptions.image = new ConstantProperty<>(facilityImg);
                                            billboardGraphicsOptions.horizontalOrigin = new ConstantProperty<>(HorizontalOrigin.CENTER());
                                            billboardGraphicsOptions.verticalOrigin = new ConstantProperty<>(VerticalOrigin.BOTTOM());
                                            BillboardGraphics billboardGraphics = new BillboardGraphics(billboardGraphicsOptions);
                                            EntityOptions entityOptions = new EntityOptions();
                                            entityOptions.billboard = billboardGraphics;
                                            entityOptions.position = new ConstantPositionProperty(Cartesian3.fromDegrees(-75.59777, 40.03883));
                                            _csPanelAbstract.getViewer().entities().add(new Entity(entityOptions));

                                            billboardGraphicsOptions = new BillboardGraphicsOptions();
                                            billboardGraphicsOptions.image = new ConstantProperty<>(logoImg);
                                            billboardGraphicsOptions.horizontalOrigin = new ConstantProperty<>(HorizontalOrigin.CENTER());
                                            billboardGraphicsOptions.verticalOrigin = new ConstantProperty<>(VerticalOrigin.BOTTOM());
                                            billboardGraphicsOptions.pixelOffset = new ConstantProperty<>(new Cartesian2(0.0, -facilityHeight));
                                            billboardGraphicsOptions.pixelOffsetScaleByDistance = new ConstantProperty<>(new NearFarScalar(1.0e3, 1.0, 1.5e6, 0.0));
                                            billboardGraphicsOptions.translucencyByDistance = new ConstantProperty<>(new NearFarScalar(1.0e3, 1.0, 1.5e6, 0.1));
                                            billboardGraphics = new BillboardGraphics(billboardGraphicsOptions);
                                            entityOptions = new EntityOptions();
                                            entityOptions.billboard = billboardGraphics;
                                            entityOptions.position = new ConstantPositionProperty(Cartesian3.fromDegrees(-75.59777, 40.03883));
                                            _csPanelAbstract.getViewer().entities().add(new Entity(entityOptions));
                                        }
                                    },
                                    new Reject<Void>() {
                                        @Override
                                        public void onRejected(Void value) {
                                            LOGGER.info("facility imagery not loaded");
                                        }
                                    }
                            );
                        }
                    },
                    new Reject<Void>() {
                        @Override
                        public void onRejected(Void value) {
                            LOGGER.info("Cesium_Logo_overlay imagery not loaded");
                        }
                    }
            );
        }

        private void addMarkerBillboards() {
            BillboardGraphicsOptions billboardGraphicsOptions = new BillboardGraphicsOptions();
            billboardGraphicsOptions.image = new ConstantProperty<>(GWT.getModuleBaseURL() + "images/whiteShapes.png");
            billboardGraphicsOptions.imageSubRegion = new ConstantProperty<>(new BoundingRectangle(49, 43, 18, 18));
            billboardGraphicsOptions.color = new ConstantProperty<>(Color.LIME());
            EntityOptions entityOptions = new EntityOptions();
            entityOptions.billboard = new BillboardGraphics(billboardGraphicsOptions);
            entityOptions.position = new ConstantPositionProperty(Cartesian3.fromDegrees(-75.59777, 40.03883));
            _csPanelAbstract.getViewer().entities().add(new Entity(entityOptions));

            billboardGraphicsOptions = new BillboardGraphicsOptions();
            billboardGraphicsOptions.image = new ConstantProperty<>(GWT.getModuleBaseURL() + "images/whiteShapes.png");
            billboardGraphicsOptions.imageSubRegion = new ConstantProperty<>(new BoundingRectangle(61, 23, 18, 18));
            billboardGraphicsOptions.color = new ConstantProperty<>(new Color(0, 0.5, 1.0, 1.0));
            entityOptions = new EntityOptions();
            entityOptions.billboard = new BillboardGraphics(billboardGraphicsOptions);
            entityOptions.position = new ConstantPositionProperty(Cartesian3.fromDegrees(-84.0, 39.0));
            _csPanelAbstract.getViewer().entities().add(new Entity(entityOptions));

            billboardGraphicsOptions = new BillboardGraphicsOptions();
            billboardGraphicsOptions.image = new ConstantProperty<>(GWT.getModuleBaseURL() + "images/whiteShapes.png");
            billboardGraphicsOptions.imageSubRegion = new ConstantProperty<>(new BoundingRectangle(67, 80, 14, 14));
            billboardGraphicsOptions.color = new ConstantProperty<>(new Color(0.5, 0.9, 1.0, 1.0));
            entityOptions = new EntityOptions();
            entityOptions.billboard = new BillboardGraphics(billboardGraphicsOptions);
            entityOptions.position = new ConstantPositionProperty(Cartesian3.fromDegrees(-70.0, 41.0));
            _csPanelAbstract.getViewer().entities().add(new Entity(entityOptions));

            billboardGraphicsOptions = new BillboardGraphicsOptions();
            billboardGraphicsOptions.image = new ConstantProperty<>(GWT.getModuleBaseURL() + "images/whiteShapes.png");
            billboardGraphicsOptions.imageSubRegion = new ConstantProperty<>(new BoundingRectangle(27, 103, 22, 22));
            billboardGraphicsOptions.color = new ConstantProperty<>(Color.RED());
            entityOptions = new EntityOptions();
            entityOptions.billboard = new BillboardGraphics(billboardGraphicsOptions);
            entityOptions.position = new ConstantPositionProperty(Cartesian3.fromDegrees(-73.0, 37.0));
            _csPanelAbstract.getViewer().entities().add(new Entity(entityOptions));

            billboardGraphicsOptions = new BillboardGraphicsOptions();
            billboardGraphicsOptions.image = new ConstantProperty<>(GWT.getModuleBaseURL() + "images/whiteShapes.png");
            billboardGraphicsOptions.imageSubRegion = new ConstantProperty<>(new BoundingRectangle(105, 105, 18, 18));
            billboardGraphicsOptions.color = new ConstantProperty<>(Color.YELLOW());
            entityOptions = new EntityOptions();
            entityOptions.billboard = new BillboardGraphics(billboardGraphicsOptions);
            entityOptions.position = new ConstantPositionProperty(Cartesian3.fromDegrees(-79.0, 35.0));
            _csPanelAbstract.getViewer().entities().add(new Entity(entityOptions));
        }

        private void reset() {
            _csPanelAbstract.getViewer().camera.flyHome(0);
            _csPanelAbstract.getViewer().entities().removeAll();
        }
    }

    @Inject
    public Billboards(ShowcaseExampleStore store) {
        super("Billboards", "Cesium billboards", new String[]{"Showcase", "Cesium", "3d", "Billboards"}, store);
    }

    @Override
    public void buildPanel() {
        final ViewerPanel csVPanel = new ViewerPanel();

        ListBox billboardsLBox = new ListBox();
        billboardsLBox.addItem("Add billboard", "0");
        billboardsLBox.addItem("Set billboard properties at creation", "1");
        billboardsLBox.addItem("Change billboard properties", "2");
        billboardsLBox.addItem("Size billboard in meters", "3");
        billboardsLBox.addItem("Add multiple billboards", "4");
        billboardsLBox.addItem("Scale by viewer distance", "5");
        billboardsLBox.addItem("Fade by viewer distance", "6");
        billboardsLBox.addItem("Offset by viewer distance", "7");
        billboardsLBox.addItem("Add marker billboards", "8");
        billboardsLBox.addChangeHandler(new ChangeHandler() {
            @Override
            public void onChange(ChangeEvent changeEvent) {
                ListBox source = (ListBox)changeEvent.getSource();
                csVPanel.reset();
                switch (source.getSelectedValue()) {
                    case "0": csVPanel.addBillboard(); break;
                    case "1": csVPanel.setBillboardProperties(); break;
                    case "2": csVPanel.changeBillboardProperties(); break;
                    case "3": csVPanel.sizeBillboardInMeters(); break;
                    case "4": csVPanel.addMultipleBillboards(); break;
                    case "5": csVPanel.scaleByDistance(); break;
                    case "6": csVPanel.fadeByDistance(); break;
                    case "7": csVPanel.offsetByDistance(); break;
                    case "8": csVPanel.addMarkerBillboards();
                    default: break;
                }
            }
        });

        AbsolutePanel aPanel = new AbsolutePanel();
        aPanel.add(csVPanel);
        aPanel.add(billboardsLBox, 20, 20);

        contentPanel.add(new HTML("<p>This example shows billboards on Cesium/p>"));
        contentPanel.add(billboardsLBox);
        contentPanel.add(aPanel);

        initWidget(contentPanel);
    }

    @Override
    public String[] getSourceCodeURLs() {
        String[] sourceCodeURLs = new String[1];
        sourceCodeURLs[0] = GWT.getModuleBaseURL() + "examples/" + "Billboards.txt";
        return sourceCodeURLs;
    }
}
